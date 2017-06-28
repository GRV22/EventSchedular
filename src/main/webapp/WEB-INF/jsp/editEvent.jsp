<%--
  Created by IntelliJ IDEA.
  User: ganesh.kumar
  Date: 6/25/17
  Time: 7:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit this Event</title>

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
            /**document.body.appendChild(element);
            document.body.appendChild(button);**/
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


<h1>Edit an Event</h1>
<form action="${pageContext.request.contextPath}/events/${eventInfo.getEventId()}" method="post">
    <table>
        <tr><td>Event Name:</td><td><input type="text" name="eventName" value="${eventInfo.getEventName()}"/></td></tr>
        <tr><td>Summary:</td><td><input type="text" name="summary" value="${eventInfo.getSummary()}"/></td></tr>
        <tr><td>Start Time:</td><td><input type="text" name="startTime" value="${eventInfo.getStartTime()}"/></td></tr>
        <tr><td>End Time:</td><td><input type="text" name="endTime" value="${eventInfo.getEndTime()}"/></td></tr>
        <tr>
            <td>Attendees :</td>
            <td>

                <p class="setting">
                    <input type="button" id="add" name="add" class="addperson" value="Add row" onclick="addAttendee();">
                    <span id="attendees">
                        <c:forEach var="guest" items="${eventInfo.getAttendee()}">
                            <script type="text/javascript">
                                var attendeeId = "attendee"+'${guest}';

                                var element = document.createElement("input");
                                element.setAttribute("type", "email");
                                element.setAttribute("name", "attendee");
                                element.setAttribute("id",attendeeId);
                                element.setAttribute("value",'${guest}');

                                var removeButtonId = "removeAttendee"+'${guest}';

                                var button = document.createElement("button");
                                button.setAttribute("id",removeButtonId);
                                buttonText = document.createTextNode("Remove");
                                button.appendChild(buttonText);
                                button.setAttribute("onclick","removeAttendee(\""+attendeeId+"\","+"\""+removeButtonId+"\");");

                                document.getElementById("attendees").appendChild(element);
                                document.getElementById("attendees").appendChild(button);
                            </script>
                        </c:forEach>
                    </span>
                    <br><br>
                </p>
            </td>
        </tr>
        <tr><td colspan="2"><input type="submit" value="Update Event"/></td></tr>
    </table>
</form>


</body>
</html>
