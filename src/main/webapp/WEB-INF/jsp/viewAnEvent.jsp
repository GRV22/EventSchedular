<%--
  Created by IntelliJ IDEA.
  User: ganesh.kumar
  Date: 6/26/17
  Time: 4:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Event Details</title>
</head>
<body>

<a href="${pageContext.request.contextPath}/home">Go to Home</a>


<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>Event Details</h2></caption>
        <tr>
            <th>Event Name</th>
            <th>Event Description</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Guests</th>
            <th>Actions</th>
        </tr>
        <tr>
                <td>${event.getEventName()}</td>
                <td>${event.getSummary()}</td>
                <td>${event.getStartTime()}</td>
                <td>${event.getEndTime()}</td>
                <td>${event.getAttendee()}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/events/<c:out value='${event.getEventId()}' />/edit">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <form action="${pageContext.request.contextPath}/events/${event.getEventId()}/delete" method="post">
                        <input type="submit" value="Delete"/>
                    </form>
                </td>
            </tr>
    </table>
</div>

</body>
</html>
