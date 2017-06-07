/**
 * Created by liangyh on 2/26/2017.
 */
//一个简单的哈希表实现
var HashMap = {
    set : function(key,value){this[key] = value},
    get : function(key){return this[key]},
    contains : function(key){return this.get(key) == null?false:true},
    remove : function(key){delete this[key]}
}

