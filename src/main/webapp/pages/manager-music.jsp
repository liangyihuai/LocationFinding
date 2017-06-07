<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>manager-music</title>
    <link href="../../css/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" type="text/javascript" src="../../js/jquery.min.js"></script>
    <script language="javascript" type="text/javascript" src="../../css/bootstrap/dist/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div align="center"><h1>管理员管理窗口</h1></div>
    <div >

        <div class="tabbable" id="tabs-157328">
            <ul class="nav nav-tabs">
                <li  class="active">
                    <a href="#login-tab-id" data-toggle="tab" onclick="">管理员登录</a>
                </li>
                <li>
                    <a href="#user-tab-id" data-toggle="tab" onclick="getAllUsers()">会员管理</a>
                </li>
                <li>
                    <a href="#music-upload-id" data-toggle="tab">上传音乐</a>
                </li>
                <li>
                    <a href="#music-manager-tab-id" data-toggle="tab">音乐管理</a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane" id="music-upload-id">
                    <form class="form-horizontal" id="uploadMusicForm" style="margin-top: 10px">
                        <div class="form-group">
                            <label for="publishDate" class="col-sm-2 control-label">发布时间</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="publishDate" name="publishDate" placeholder="请输入发布时间">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="singerName" class="col-sm-2 control-label">歌手名字</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="singerName" name="singerName" placeholder="请输入歌手名字">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="musicName" class="col-sm-2 control-label">歌名</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="musicName" name="musicName" placeholder="请输入歌名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="categoryId" class="col-sm-2 control-label">类别</label>
                            <div class="col-sm-3">
                                <select class="form-control" id="categoryId" name="categoryId" placeholder="请选择类别">
                                    <option value="1">内地</option>
                                    <option value="2">港台</option>
                                    <option value="3">欧美</option>
                                    <option value="4">韩国</option>
                                    <option value="5">日本</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="style" class="col-sm-2 control-label">音乐风格</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="style" name="style" placeholder="请输入音乐风格">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="imageFile" class="col-sm-2 control-label">音乐图片</label>
                            <div class="col-sm-3">
                                <input type="file" class="form-control" id="imageFile" name="imageFile" placeholder="图片">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="musicFile" class="col-sm-2 control-label">音乐文件</label>
                            <div class="col-sm-3">
                                <input type="file" class="form-control" id="musicFile" name="musicFile" placeholder="音乐文件">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="button" class="btn btn-default" onclick="uploadMusic()">上传</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="tab-pane" id="user-tab-id">
                    <div class="form-group" style="margin-top: 10px">
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="userManager-search-id" name="categoryId" placeholder="请输入用户名">
                        </div>
                        <button for="userManager-search-id" onclick="searchUser()"  class="btn btn-default">查询用户</button>
                    </div>
                    <table class="table table-hover">
                        <tbody id="user-tbody-id"></tbody>
                    </table>

                </div>
                <div class="tab-pane" id="music-manager-tab-id">

                    <div class="form-group" style="margin-top: 10px">
                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="musicManager-search-id" name="categoryId" placeholder="请输入音乐名字">
                        </div>
                        <button for="music-search-id" onclick="searchMusic()"  class="btn btn-default">查询音乐</button>
                    </div>

                    <div class="modal-body">
                        <table class="table table-hover">
                            <tbody id="musicManager-tbody-id"></tbody>
                        </table>
                    </div>
                </div>

                <div class="tab-pane active" id="login-tab-id">
                    <form class="form-horizontal" role="form" style="margin-top: 50px">
                        <div class="form-group">
                            <label for="name-login-id"  class="col-sm-2 control-label">名字</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control" id="name-login-id" placeholder="请输入名字">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password-login-id"  class="col-sm-2 control-label">密码</label>
                            <div class="col-sm-5">
                                <input type="password" class="form-control" id="password-login-id" placeholder="请输入密码">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-3">
                                <button type="submit" class="btn btn-default" onclick="login()">登录</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>

    </div>
</div>
<script language="javascript" type="text/javascript" src="../../js/musicPlay.js"></script>
<script language="javascript" type="text/javascript" src="../../js/rankList.js"></script>

<script type="text/javascript">

    function uploadMusic() {
        if(document.getElementById('imageFile').value == ''){
            alert("请选择imageFile文件")
            return ;
        }
        if(document.getElementById('musicFile').value == ''){
            alert("请选择musicFile文件")
            return ;
        }
        var form = new FormData(document.getElementById("uploadMusicForm"));

        $.ajax({
            url:"/music/addMusic.do",
            type:"post",
            data:form,
            processData:false,
            contentType:false,
            success:function(data){
                alert(data);
            }, error:function(e){
                alert(e.responseText);
            }
        });
    }

    function searchUser(){
        var userName = document.getElementById("userManager-search-id").value;
        if(userName == ''){
            alert("请输入用户名！");
            return;
        }
        $.ajax({
            url:"/user/queryUser.do?userName="+userName,
            type:"get",
            success:function(user){
                var userList = [];
                userList.push(user);
                showUsers_manager(userList);
            }
        });
    }

    function showUsers_manager(userList) {
        var tbody = document.getElementById("user-tbody-id")
        tbody.innerHTML = "";
        var index = 1;
        for(var i in userList){
            var user = userList[i];

            var tr = document.createElement('tr')
            var td_no = document.createElement('td')
            td_no.innerHTML = (index++);
            var td_userName = document.createElement('td');
            td_userName.innerHTML = user.nickName;
            td_userName.setAttribute('style','width:300px');


            var td_delete = document.createElement('td')
            td_delete.setAttribute('style', 'margin-left:10px;float: left')
            var a_delete = document.createElement('a');
            a_delete.onclick = deleteUser(user.id);
            var img_delete = document.createElement('img');
            img_delete.setAttribute('width', '20');
            img_delete.setAttribute('src', '../../images/icon/delete.png')
            a_delete.appendChild(img_delete);
            td_delete.appendChild(a_delete);

            tr.appendChild(td_no);
            tr.appendChild(td_userName)
            tr.appendChild(td_delete)

            tbody.appendChild(tr);
        }
    }

    function getAllUsers() {
        $.ajax({
            url:"/user/allUser.do",
            type:"get",
            success:function(userList){
                showUsers_manager(userList);
            }
        });
    }

    function deleteUser(userId) {
        return function () {
            doDeleteUser(userId);
        }
    }

    function doDeleteUser(userId){
        var r =confirm("确定要删除该用户吗？");
        if (r==true) {
            $.ajax({
                url: "/user/delete.do?userId=" + userId,
                type: "get",
                success: function (data) {
                    if (data > 0) {
                        alert("删除成功！");
                        getAllUsers();
                    } else {
                        alert("删除失败！");
                    }
                }
            });
        }
    }

    function showMusics_musicManager(musicList, tbodyId) {
        var tbody = document.getElementById(tbodyId)
        tbody.innerHTML = "";
        var index = 1;
        for(var i in musicList){
            var music = musicList[i];

            var tr = document.createElement('tr')
            var td_no = document.createElement('td')
            td_no.innerHTML = (index++);
            var td_musicName = document.createElement('td');
            td_musicName.innerHTML = music.musicName;
            td_musicName.setAttribute('style','width:300px')
            var td_singer = document.createElement('td');
            td_singer.innerHTML = music.singerName;
            td_singer.setAttribute('style', 'width:150px')

            var td_play = document.createElement('td')
            td_play.setAttribute('style', 'margin-left:10px;float: left');
            var a_play = document.createElement('a');
            a_play.onclick = doForwardToPlayerList(music.id);
            var img_play = document.createElement('img');
            img_play.setAttribute('width', '23');
            img_play.setAttribute('src', '../../images/icon/play.png')
            a_play.appendChild(img_play);
            td_play.appendChild(a_play);

            var td_download = document.createElement('td');
            td_download.setAttribute('style', 'margin-left:10px;float: left');
            var a_download = document.createElement('a');
            a_download.onclick = doDownload(music.music, music.musicName);
            var img_download = document.createElement('img');
            img_download.setAttribute('width', '20');
            img_download.setAttribute('src', '../../images/icon/download.png');
            a_download.appendChild(img_download);
            td_download.appendChild(a_download);

            var td_delete = document.createElement('td')
            td_delete.setAttribute('style', 'margin-left:10px;float: left')
            var a_delete = document.createElement('a');
            a_delete.onclick = deleteMusic(music.id);
            var img_delete = document.createElement('img');
            img_delete.setAttribute('width', '20');
            img_delete.setAttribute('src', '../../images/icon/delete.png')
            a_delete.appendChild(img_delete);
            td_delete.appendChild(a_delete);

            tr.appendChild(td_no);
            tr.appendChild(td_musicName)
            tr.appendChild(td_singer)
            tr.appendChild(td_play)
            tr.appendChild(td_download)
            tr.appendChild(td_delete)

            tbody.appendChild(tr);
        }
    }

    function searchMusic(){
        var musicName = document.getElementById("musicManager-search-id").value;
        $.ajax({
            url:"/music/search.do?musicName="+musicName,
            type:"get",
            success:function(musicList){
                saveMusicList(musicList)
                showMusics_musicManager(musicList, 'musicManager-tbody-id');
            }
        });
    }

    function deleteMusic(musicId) {
        return function () {
            doDeleteMusic(musicId);
        }
    }

    function doDeleteMusic(musicId) {
        var r =confirm("确定要删除这首音乐吗？");
        if (r==true) {
            $.ajax({
                url:"/music/delete.do?musicId="+musicId,
                type:"get",
                success:function(data){
                    if(data > 0){
                        alert("删除成功！");
                        searchMusic();
                    }else{
                        alert("删除失败！");
                    }
                }
            });
        }
    }

    function login() {
        var name = document.getElementById('name-login-id').value;
        var password = document.getElementById('password-login-id').value;
        if(name == ''|| password == ''){
            alert("用户名或密码不能为空");
            return ;
        }
        if(name == 'root' && password == 'root'){
            alert("登录成功!");
        }else{
            alert("用户名或者密码不正确！");
        }
    }
</script>
</body>
</html>
