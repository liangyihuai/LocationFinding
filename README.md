# LocationFinding

这个是一个Web应用系统，能够从海量的手机基站数据中挖据出用户的主要活动地点，比如工作地点和居住地点。算法的核心使用Scala语言编写，执行在Spark计算引擎上面。


总共分为两个模块，一个是web网站模块，也就是这个LocationFinding；另一个是算法模块，使用scala语言编写的Spark代码，它不在这个repository中，入口为：[https://github.com/liangyihuai/AlgorithmCore](https://github.com/liangyihuai/AlgorithmCore)
。前面的模块依赖后面的模块。LocationFinding模块让用户能够通过简单的Web UI就能够操作使用该算法，同时为用户提供了可视化数据的功能。

需要注意的是，LocationFinding模块不是通过maven添加算法模块的依赖的，而是通过在LocationFinding这个项目的/WEB-INF/lib下面添加算法的jar包，并且jar包的内容应该包含：algorithm。
其他三方依赖是通过maven添加的。

下面的代码是LocationFinding搜索algorithm的jar包过程。
```
   private String getAlgorithmJarArchivePath(String applicationContextPath){
        File file = new File(applicationContextPath+"/WEB-INF/lib/");
        if(file.exists()&&file.isDirectory()){
            File [] files = file.listFiles();
            for(File f: files){
                if(f.getName().contains("algorithm")){
                    return file.getPath()+"/"+f.getName();
                }
            }
        }
       return "";
    }
```

详见blog：[http://blog.csdn.net/liangyihuai/article/details/72896248](http://blog.csdn.net/liangyihuai/article/details/72896248)
