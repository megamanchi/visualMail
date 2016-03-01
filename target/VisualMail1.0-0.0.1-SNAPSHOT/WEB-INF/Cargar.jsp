<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<spring:url value="/resources/js/jquery-1.7.1.min.js" var="jqueryJs" />
    <spring:url value="/resources/js/d3.min.js" var="d3Js" />
    <spring:url value="/resources/js/fisheye.js" var="fisheyeJs" />
	<spring:url value="/resources/css/VisualMail.css" var="mainCss" />
	
	<script src="${jqueryJs}"></script>
	<link href="${mainCss}" rel="stylesheet" />
    <script src="${d3Js}"></script>
     <script src="${fisheyeJs}"></script>
    

	
    <style>

.node circle {
  fill: #fff;
  stroke: steelblue;
  stroke-width: 1.5px;
}

.node {
  font: 10px sans-serif;
}

.link {
  fill: none;
  stroke: #ccc;
  stroke-width: 1.5px;
}

</style>
</head>



 <meta charset="utf-8">

<body>
	<h1>Visual Mail Prototipo</h1>
	
	<h3>Mensaje desde el controlador: ${name}</h3>	
	
<script>

	function draw()
	{
    
		var width = 960,
	    height = 500;

	var color = d3.scale.category20();

	var force = d3.layout.force()
	    .charge(-120)
	    .linkDistance(30)
	    .size([width, height])
		
		
	var groups;


    var nodes;


    var cluster = d3.layout.cluster()
    .size([height, width - 160]);

    var diagonal = d3.svg.diagonal()
    .projection(function (d) { return [d.y, d.x]; });
	
    var svg;
    if (d3.select("svg")[0][0] == null){
    svg = d3.select("#tree").append("svg")
    .attr("width", width)
    .attr("height", height)
  	.append("g")
    .attr("transform", "translate(40,0)");
    }else
    	{   	
    	
    	$( "#tree svg").remove()
        svg = d3.select("#tree").append("svg")        
        .attr("width", width)
        .attr("height", height)
      	.append("g")
        .attr("transform", "translate(40,0)");
    	}
    
    
    var fisheye = d3.fisheye.circular()
    .radius(200)
    .distortion(5);
    
    
    svg.on("mousemove", function() {
    	  fisheye.focus(d3.mouse(this));
    	});
    

    d3.json("/VisualMail1.0/resources/js/flare.json", function (root) {
      //  if (error) throw error;
       
      
   /*   force
      .nodes(root.nodes)
      .links(root.links)
      .start();*/

       // groups = json;
       nodes = cluster.nodes(root),
      links = cluster.links(nodes);
 	  


        var link = svg.selectAll(".link")
      .data(links)
    .enter().append("path")
      .attr("class", "link")
      .attr("d", diagonal);
        
        var node = svg.selectAll(".node")
      .data(nodes)
    .enter().append("g")
      .attr("class", "node")
      .attr("transform", function (d) { return "translate(" + d.y + "," + d.x + ")"; })

       node.append("circle")
      .attr("r", 7.5)
      .style("fill", function (d) { return d.color; });
        
        
        
        node.on({
         //   "mouseover": function() { /*alert(this.childNodes[1].childNodes[0].nodeValue);*/},
            "mouseout":  function() { /* do stuff */ }, 
            "click":  function() {
            	d3.select(this).style({opacity:'1.0'});
            	alert(this.childNodes[1].childNodes[0].nodeValue); }, 
          });
        
      
                    
        

        node.append("text")
      .attr("dx", function (d) { return d.children ? -8 : 8; })
      .attr("dy", 3)
      .style("text-anchor", function (d) { return d.children ? "end" : "start"; })
      .text(function (d) { return d.name; });


       node.append("text")
      .attr("dy", 3)
      .style("text-anchor", function (d) { return d.children ? "end" : "start"; })
      .text(function (d) { return d.element; });
       
       force.on("tick", function() {
    	    link.attr("x1", function(d) { return d.source.x; })
    	        .attr("y1", function(d) { return d.source.y; })
    	        .attr("x2", function(d) { return d.target.x; })
    	        .attr("y2", function(d) { return d.target.y; });

    	    node.attr("cx", function(d) { return d.x; })
    	        .attr("cy", function(d) { return d.y; });
    	  });
       
       
       

      //.style("fill", function (d) { return d.color; })
    });

    d3.select(self.frameElement).style("height", height + "px");
    
    
    
  
    

	}
    
    $(function () {
        $("#boton").click(function () {
        	
            draw();
            
        }
      );
    });
    function tick() {
    	  link.attr("x1", function(d) { return d.source.x; })
    	      .attr("y1", function(d) { return d.source.y; })
    	      .attr("x2", function(d) { return d.target.x; })
    	      .attr("y2", function(d) { return d.target.y; });

    	  node.attr("cx", function(d) { return d.x; })
    	      .attr("cy", function(d) { return d.y; });
    	}

</script>

<div id="tree">
</div>

<div>
<table style="width:100%">
  <tr>
    <td><input type="button" value="send" id="boton"><input type="button" value="recibe" id="boton"></td>    <td></td>
  </tr>
  <tr>
    <td><textarea rows="4" cols="50">
Felipe:Mejor plataforma de desarrollo web
</textarea></td>
  </tr>
</table>

</div>
 
</body> 
</html>
 