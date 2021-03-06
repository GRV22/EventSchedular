<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ganesh.kumar
  Date: 6/20/17
  Time: 10:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*,java.util.*,com.ganesh.UI.DTO.EventInfo" %>
<html>
<head>
    <title>All Upcoming Events</title>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <script type="text/javascript">


        function sendRequest(){
//jQuery syntax for sending ajax request
            var response = $.ajax({
//options follow
                url:'events/123', //the url at which you want to send request
               // data:'id=1', //the request parameters which are to be sent to server.
                method:'DELETE', //type of request. Possible values are Get, Post, Put, Delete
                success: function(){
                    alert("Request Successful");
                }});
            console.log(response);
        }


    </script>
    <script type="text/javascript">
        function sendRequest1(obj) {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    console.log("delete success");
                    alert(obj);
                }
            };
            xhttp.open("DELETE", "events/123");
            xhttp.send(obj);
        }

    </script>

</head>
<body>
<a href="${pageContext.request.contextPath}/home">Go to Home</a>


<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Events</h2></caption>
        <tr>
            <th>Event Name</th>
            <th>Event Description</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Guests</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="event" items="${events}">
            <tr>
                <td><c:out value="${event.getEventName()}" /></td>
                <td><c:out value="${event.getSummary()}" /></td>
                <td><c:out value="${event.getStartTime()}" /></td>
                <td><c:out value="${event.getEndTime()}" /></td>
                <td><c:out value="${event.getAttendee()}" /></td>
                <td>
                        <a href="${pageContext.request.contextPath}/events/<c:out value='${event.getEventId()}' />/edit">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                    <form action="${pageContext.request.contextPath}/events/${event.getEventId()}/delete" method="post">
                        <input type="submit" value="Delete"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
