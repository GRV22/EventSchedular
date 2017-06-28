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
    <script>
        var cnt = 1;
        function addAttendee() {

            var attendeeId = "attendee"+cnt;

            var element = document.createElement("input");
            element.setAttribute("type", "email");
            element.setAttribute("name", "attendee");
            element.setAttribute("id",attendeeId);

            var removeButtonId = "removeAttendee"+cnt;

            var button = document.createElement("button");
            button.setAttribute("id",removeButtonId);
            buttonText = document.createTextNode("Remove");
            button.appendChild(buttonText);
            button.setAttribute("onclick","removeAttendee(\""+attendeeId+"\","+"\""+removeButtonId+"\");");

            document.getElementById("attendees").appendChild(element);
            document.getElementById("attendees").appendChild(button);
            cnt = cnt+1;
        }

        function removeAttendee(attendeeId,attendeeButtonId)
        {
            console.log(attendeeId);
            console.log(attendeeButtonId);
            document.getElementById(attendeeId).remove();
            document.getElementById(attendeeButtonId).remove();
        }


    </script>
</head>
<body>

<a href="${pageContext.request.contextPath}/home">Go to Home</a>


<h1>Add New Event</h1>
    <form action="${pageContext.request.contextPath}/events" method="post">
    <table>
        <tr><td>Event Name:</td><td><input type="text" name="eventName" value="Test Event"/></td></tr>
        <tr><td>Description:</td><td><input type="text" name="summary"/></td></tr>
        <tr><td>Start Time:</td><td><input type="text" name="startTime" value="YYYY-MM-DDThh:mmTZD"/></td></tr>
        <tr><td>End Time:</td><td><input type="text" name="endTime" value="YYYY-MM-DDThh:mmTZD"/></td></tr>
        <tr>
            <td>Attendees :</td>
            <td>
                <p class="setting">
                    <input type="button" id="add" name="add" class="addperson" value="Add row" onclick="addAttendee();">
                    <div id="attendees"></div>
                    <br><br>
                </p>
            </td>
        </tr>
        <tr><td colspan="2"><input type="submit" value="Add an Event"/></td></tr>
    </table>
    </form>


</body>
</html>
