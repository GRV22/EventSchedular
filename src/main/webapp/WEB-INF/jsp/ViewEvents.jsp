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
            $.ajax({
//options follow
                url:'receiveRequest', //the url at which you want to send request
               // data:'id=1', //the request parameters which are to be sent to server.
                method:'Delete', //type of request. Possible values are Get, Post, Put, Delete
                success: function(){
                    alert("Request Successful");
                }});
        }


    </script>

</head>
<body>
<a href="home">Go to Home</a>


<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Events</h2></caption>
        <tr>
            <th >Event ID</th>
            <th>Event Name</th>
            <th>Event Description</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Guests</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="event" items="${events}">
            <tr>
                <td ><c:out value="${event.getEventId()}" /></td>
                <td><c:out value="${event.getEventName()}" /></td>
                <td><c:out value="${event.getSummary()}" /></td>
                <td><c:out value="${event.getStartTime()}" /></td>
                <td><c:out value="${event.getEndTime()}" /></td>
                <td><c:out value="${event.getAttendee()}" /></td>
                <td>
                    <a href="/edit?id=<c:out value='${event.toString()}' />">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <form action="events/<c:out value='${event.getEventId()}' />" method="delete">
                        <input type="submit" value="Delete"/>
                    </form>
                   <%-- <form action="delete/events/${event.getEventId()}" method="get">--%>
                    <form action="/delete?id=${event.getEventId()}" method="get">
                        <input type="submit" value="TmpDelete"/>
                    </form>
                    <a href="/delete?id=${event.getEventId()}">TempDelete2</a>
                    <input type="submit" value="TmpDelete1" onclick="sendRequest()"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
