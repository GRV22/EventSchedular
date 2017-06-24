<%--
  Created by IntelliJ IDEA.
  User: ganesh.kumar
  Date: 6/19/17
  Time: 11:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new Event</title>
</head>
<body>


<script type="text/javascript">

    function addAttendee() {

        var element = document.createElement("input");
        element.setAttribute("type", "email");
        element.setAttribute("name", "attendee");
        var spanvar = document.getElementById("attendees");
        spanvar .appendChild(element).appendChild("<br>");

    }
</script>

<!--
<p><b>First Name:</b>< request.getParameter("firstName")></p>


<div id="Insert">
    <p class="setting">
        <input type="button" id="add" name="add" class="addperson" value="Add row" onclick="addAttendee();">
        <span id="attendees"></span>
        <br><br>
    </p>
</div>
-->



<h1>Add New Event</h1>
    <form action="addEvent" method="post">
    <table>
        <tr><td>Event Name:</td><td><input type="text" name="eventName" value="Test Event"/></td></tr>
        <tr><td>Summary:</td><td><input type="text" name="summary"/></td></tr>
        <tr><td>Start Time:</td><td><input type="text" name="startTime" value="YYYY-MM-DDThh:mmTZD"/></td></tr>
        <tr><td>End Time:</td><td><input type="text" name="endTime" value="YYYY-MM-DDThh:mmTZD"/></td></tr>
        <tr>
            <td>Attendees :</td>
            <td>
                <!--<input type="button" id="addAttendee" name="addAttendee" value="Add Attendee" onclick="addAttendee();">-->
                <p class="setting">
                    <input type="button" id="add" name="add" class="addperson" value="Add row" onclick="addAttendee();">
                    <span id="attendees"></span>
                    <br><br>
                </p>
            </td>
     <!--       <td id="attendees">
            <input type="button" id="addAttendee" name="addAttendee" value="Add Attendee" onclick="addAttendee();">
            </td>-->
        </tr>
        <tr><td colspan="2"><input type="submit" value="Add an Event"/></td></tr>
    </table>
    </form>


</body>
</html>
