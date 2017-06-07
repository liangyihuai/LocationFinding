/**
 * Created by liangyh on 2/27/17.
 */

var url = "http://api.map.baidu.com/geoconv/v1/?from=1&to=5&ak=A4749739227af1618f7b0d1b588c0e85&callback=?";
var numfix = 4;
var rawDataSet = null;
var clusteredData = null;
var map = null;

$(document).ready(function () {
    map = new BMap.Map("l-map");   // 创建地图实例
    getDataAndInitial();
});

function getDataAndInitial() {
    var thisURL = decodeURI(document.URL);
    var  paramsStr =thisURL.split('?')[1];

    /*"/pages/datasetMap.html?dataset="+dataset+"&resultset="+resultset+"&longitudeIndex="+longitudeIndex+
     "&latitudeIndex="+latitudeIndex+"&separator="+separator);*/

    $.ajax({
        url:"/display/data.do?"+paramsStr,
        type:"post",
        success:function(data){
            rawDataSet = data.rawDataSet;
            clusteredData = data.clusteredDataSet;

            initialLayout();
            initialMapData();
        }
    });
}

function initialLayout() {
    var offsetSize = new BMap.Size(0,0); //标注的偏移量
    var marker = new BMap.Marker();//创建聚类结果点的标注
    map.centerAndZoom(new BMap.Point(rawDataSet[0].longitude,rawDataSet[0].latitude), 15);//初始化地图，设置中心点坐标和地图级别

    map.enableScrollWheelZoom();
    map.addControl(new BMap.NavigationControl());//添加默认缩放平移控件

    map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT}));//地图比例尺
}

function initialMapData() {
    var rawPoints = [];
    for(var i in rawDataSet){
        rawPoints.push(new Point(rawDataSet[i].longitude, rawDataSet[i].latitude));
    }

    var clusterPoints = [];
    for(var i in clusteredData){
        clusterPoints.push(new Point(clusteredData[i].longitude, clusteredData[i].latitude));
    }

    var numOneTurn = 100;//一次转换百度坐标的个数

    var total = 0;//总坐标个数统计
    var groupCount = 0;//请求的次数
    if(rawPoints.length % numOneTurn > 0){
        groupCount = Math.floor(rawPoints.length /numOneTurn)+1;
    }else{
        groupCount = (rawPoints.length/numOneTurn);
    }

    var receiveSize = 0;//用于判断时候所有的坐标是否都转换完成。
    var resultOfRawData = [];//存储所有转换后的点
    for(var i = 0; i < groupCount; i++){
        var pos = new Array();//存储转换前一个组的点
        for(var j = 0; j < numOneTurn; j++){
            if(total < rawPoints.length){
                pos.push(rawPoints[total]);
            }
            total++;
        }
        var coordinate = buildCoordinateStr(pos);
        //跨域请求，转换raw类型的坐标
        $.getJSON(url, {coords:coordinate}, function(data) {
            if(data.status != 0){
                alert("failed to get convertion raw data !");
                return ;
            }else{
                var target = data.result;
                for(var k in target){
                    var lng = target[k].x;
                    var lat = target[k].y;
                    resultOfRawData.push(new Point(lng, lat));
                }
                receiveSize += target.length;
                if(receiveSize == rawPoints.length){
                    showRawDataMasker(resultOfRawData);
                }
            }
        });
    }

    if(clusterPoints.length == 0)return ;

    var coordinateOfCluster = buildCoordinateStr(clusterPoints);
    var resultOfCluster = [];
    //跨域请求，转换cluster类型的坐标。
    $.getJSON(url, {coords:coordinateOfCluster}, function(data) {
        if(data.status != 0){
            alert("failed to get convertion data!");
            return ;
        }else{
            var target = data.result;
            for(var k in target){
                var lng = target[k].x;
                var lat = target[k].y;
                resultOfCluster.push(new Point(lng, lat));
            }
            showClusteredPointMarker(resultOfCluster);
        }
    });
}

//把普通点转换成百度地图的点对象
function toMapPoints(commonPoints) {
    var result = [];
    for(var i in commonPoints){
        var point = new BMap.Point(commonPoints[i].x, commonPoints[i].y);
        result.push(point);
    }
    return result;
}

//点类
function Point(x, y) {
    this.key = x.toFixed(numfix)+""+y.toFixed(numfix);
    this.x = x;
    this.y = y;
}

//拼接坐标数组，拼接后的结果为：x1,y1;x2,y2
function buildCoordinateStr(points) {
    var str = "";
    for(var i in points){
        str += points[i].x+","+points[i].y+";";
    }
    if(str.charAt(str.length-1) == ';'){
        return str.slice(0, str.length-1);
    }else{
        return str;
    }
}


var opts = {
    width : 20,     // 信息窗口宽度
    height: 20,     // 信息窗口高度
    title : "Longitude-Latitude:"  // 信息窗口标题
};

var hashForCount = Object.create(HashMap);//用于保存相同点的数量
var hashForObject = Object.create(HashMap);//用于保存点对象
//向地图上面添加
function addMarker(commonPoint, textContent, isUseCustomIcon){  // 创建图标对象
    var point = new BMap.Point(commonPoint.x, commonPoint.y);
    var myIcon = new BMap.Icon("../images/markers.png", new BMap.Size(23, 25), {
        // 指定定位位置。
        // 当标注显示在地图上时，其所指向的地理位置距离图标左上
        // 角各偏移10像素和25像素。您可以看到在本例中该位置即是
        // 图标中央下端的尖角位置。
        offset: new BMap.Size(10, 25)});
    // 创建标注对象并添加到地图
    var marker= null;
    if(isUseCustomIcon){
        marker = new BMap.Marker(point, {icon: myIcon});
    }else{
        marker = new BMap.Marker(point);
    }
    map.addOverlay(marker);

    //给标注上面添加文字内容
    var mySquare = new SquareOverlay(point, 10);
    map.addOverlay(mySquare);
    mySquare.setTextContent(textContent);

    marker.addEventListener("click", function(){//增加时间监听器
        var infoWindow = new BMap.InfoWindow(point.lng+", "+point.lat, opts);  // 创建信息窗口对象
        map.openInfoWindow(infoWindow, point);      // 打开信息窗口
    });
}

//把raw类型的点展示到地图上面
function showRawDataMasker(points) {
    //统计相同经纬度的点
    for(var i in points){
        var key = points[i].key;
        if(hashForCount.contains(key)){
            hashForCount.set(key, hashForCount.get(key)+1);
        }else{
            hashForCount.set(key, 1);
            hashForObject.set(key, points[i]);
        }
    }
    //显示json数据到地图上面
    for(var key in hashForObject){
        var point = hashForObject[key];
        var count = hashForCount.get(key);

        addMarker(point, count+"", true);
    }
}


//对聚类产生的点增加标注
function showClusteredPointMarker(points) {
    for(var i in points){
        addMarker(points[i], "", false);
    }
}



