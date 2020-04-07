<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
html{
      height: 100%;
    width: 100%;
}

body
{
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  width: 100%;
  overflow: hidden;
  background-color: #1b2431;
}


.container{
      width: 20vw;
    background-color: #1b2431;
    height: 20vw;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    font-size: 2rem;
    color: white;
    font-family: roboto;
    font-weight: bold;
  position:relative;
}

.container::before, .container::after
{
  position: absolute;
  content : "";
  top: -5%;
  left: -5%;
  height:110%;
  width:110%;
  border-radius:50%;
  opacity:0.5;
  filter: blur(60px);
}


.container::after
{
    background-color:#00ffff;
   z-index: -1;
  animation : backglow2 15s ease infinite;
}

.container::before
{
    background-color:#ff0072;
   z-index: -2;
  animation : backglow1 15s ease infinite;
}




@keyframes backglow1
{
  
  0%
  {
    transform: translate(10%, 10%) scale(1);
    opacity:0.5;
  }
  
   25%
  {
    transform: translate(-10%, 10%) scale(0.8);
    opacity:0.5;
  }
  
   50%
  {
    transform: translate(-10%, -10%) scale(1.2);
    opacity:0.8;
  }
  
   75%
  {
    transform: translate(10%, -10%) scale(1.1);
    opacity:0.2;
  }
  
   100%
  {
    transform: translate(10%, 10%) scale(1);
    opacity:0.5;
  }
  
  
}





@keyframes backglow2
{
  
  0%
  {
    transform: translate(-10%, -10%) scale(1);
    opacity:0.5;
  }
  
   25%
  {
    transform: translate(10%, -10%) scale(0.8);
    opacity:0.5;
  }
  
   50%
  {
    transform: translate(10%, 10%) scale(1.2);
    opacity:0.8;
  }
  
   75%
  {
    transform: translate(-10%, 10%) scale(1.1);
    opacity:0.2;
  }
  
   100%
  {
    transform: translate(-10%, -10%) scale(1);
    opacity:0.5;
  }
  
  
}

 a:link { color: white; text-decoration: none; font-size: 3rem; font-family: roboto;
    font-weight: bold;}
 a:visited {text-decoration: none; font-size: 3rem;  color: white;
    font-family: roboto;
    font-weight: bold;}
 a:hover { color: white; text-decoration: underline;}
</style>
<body>
<div class="container">
<a href="index.jsp" style="padding: 40px;">
Thank you for adoption</a>
</div>
</body>
<script type="text/javascript">
setInterval(function(){

	var time_text = '(' + time +'초 후 목록이 갱신됩니다.)';

	$("#count").text(time_text);

	time--;

},1000);
</script>
</html>