<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%String doc_id = request.getParameter("docId");
String path = ""; %>

<!DOCTYPE HTML>
<html>
  <head>
  <title><%out.print(doc_id); %></title>
  <script src="/DataCollection/js/util/pdf.js"></script>
  <script type="text/javascript">
  var doc_path = "<%=path %>";
  	PDFJS.getDocument(doc_path).then(function(pdf) {
  // Using promise to fetch the page
  pdf.getPage(1).then(function(page) {
    var scale = 1.5;
    var viewport = page.getViewport(scale);

    //
    // Prepare canvas using PDF page dimensions
    //
    var canvas = document.getElementById('the-canvas');
    var context = canvas.getContext('2d');
    canvas.height = viewport.height;
    canvas.width = viewport.width;

    //
    // Render PDF page into canvas context
    //
    var renderContext = {
      canvasContext: context,
      viewport: viewport
    };
    page.render(renderContext);
  });
});
  </script>
  </head>
  
  <body>
    <canvas id="the-canvas" style="border:1px solid black;"/>
  </body>
</html>
