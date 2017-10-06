<%@ page language="java" contentType="text/html; charset=UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="../../js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="../../js/amq_jquery_adapter.js"></script>
    <script type="text/javascript" src="../../js/amq.js"></script>
    <script>
        $(function ()
        {
            var amq = org.activemq.Amq;
            var myDestination = 'topic://ajax';
            amq.init({
                uri     : 'amq', //浏览器请求服务器端时的地址
                logging : true,//浏览器在与服务器端交互时是否打印js日志
                timeout : 20,//轮询保持的时间
                clientId: (new Date()).getTime().toString()
            });
            
            //发送消息
            $("#sendBtn").click(function ()
            {
                var msg = $("#msg").val();
                var name = $("#name").val();
                amq.sendMessage(myDestination, "<message name='" + name + "' msg='" + msg + "'/>");
                
                $("#msg").val("");
                $("#name").val("");
                
                //alert("send message")
            });
            
            //接收消息
            var myHandler =
            {
                rcvMessage: function (message)
                {
                    alert("received " + message);
                    $("#distext").append(message.getAttribute('name') + ":" + message.getAttribute('msg') + "\n");
                }
            };
            
            //表示消费者的一个ID,接受到消息回调时会用到作为标识
            //表示目的地，也可以为queue
            //表示回调函数
            amq.addListener('handler', myDestination, myHandler.rcvMessage);
        });
    </script>
</head>
<body>
    <h1>发送 ajax JMS 消息</h1>
    
    消息窗口<br>
    
    <textarea rows="10" cols="50" id="distext" readonly="readonly"></textarea><br/>
    
    昵称:<input type="text" id="name"><br/>
    
    消息:<input type="text" id="msg">
    
    <input type="button" value="发送消息" id="sendBtn"/>
</body>
</html>
