<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta name="author" content="Darko Bunic"/>
    <meta name="description" content="Drag and drop table content with JavaScript"/>
    <link rel="stylesheet" href="<%=path%>/css/style.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="<%=path%>/js/drag.js"></script>
    <!-- initialize drag and drop -->
    <script type="text/javascript">
        window.onload = function () {
            var num = 0,			// number of successfully placed elements
                    rd = REDIPS.drag;	// reference to the REDIPS.drag class
            // initialization
            rd.init();
            // set hover color
            rd.hover_color = '#9BB3DA';
            // define green elements for green cells
            rd.mark.exception.green = 'green_cell';
            rd.mark.exception.greenc0 = 'green_cell';
            rd.mark.exception.greenc1 = 'green_cell';
            rd.mark.exception.greenc2 = 'green_cell';
            // define orange elements for orange cells
            rd.mark.exception.orange = 'orange_cell';
            rd.mark.exception.orangec0 = 'orange_cell';
            rd.mark.exception.orangec1 = 'orange_cell';
            rd.mark.exception.orangec2 = 'orange_cell';

            // this function (event handler) is called after element is dropped
            REDIPS.drag.myhandler_dropped = function () {
                var msg; // message text
                // if the DIV element was placed on allowed cell then
                if (rd.target_cell.className.indexOf(rd.mark.exception[rd.obj.id]) !== -1) {
                    // make it a unmovable
                    rd.enable_drag(false, rd.obj.id);
                    // increase counter
                    num++;
                    // prepare and display message
                    if (num < 6)    msg = 'Number of successfully placed elements: ' + num;
                    else            msg = 'Well done!';
                    document.getElementById('message').innerHTML = msg;
                }
            }
        }
    </script>
</head>
<body>
<div id="drag">
    <table id="table1">
        <colgroup>
            <col width="100"/>
            <col width="50"/>
            <col width="100"/>
            <col width="100"/>
            <col width="100"/>
            <col width="100"/>
            <col width="100"/>
        </colgroup>
        <tr>
            <td class="dark"></td>
            <td class="mark blank"></td>
            <td class="dark single" title="Single content cell"></td>
            <td></td>
            <td></td>
            <td></td>
            <td class="dark single" title="Single content cell"></td>
        </tr>
        <tr>
            <td class="dark"></td>
            <td class="mark blank"></td>
            <td></td>
            <td></td>
            <td class="mark green_cell single"></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <!-- define green clone element (2 cloned elements + last element) -->
            <td class="dark">
                <div id="green" class="drag green clone climit1_2">Green</div>
            </td>
            <td class="mark blank"></td>
            <td></td>
            <td class="mark orange_cell single"></td>
            <td></td>
            <td class="mark orange_cell single"></td>
            <td></td>
        </tr>
        <tr>
            <td class="dark"></td>
            <td class="mark blank"></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <!-- define orange clone element (2 cloned elements + last element) -->
            <td class="dark">
                <div id="orange" class="drag orange clone climit1_2">Orange</div>
            </td>
            <td class="mark blank"></td>
            <td></td>
            <td class="mark green_cell single"></td>
            <td></td>
            <td class="mark green_cell single"></td>
            <td></td>
        </tr>
        <tr>
            <td class="dark"></td>
            <td class="mark blank"></td>
            <td></td>
            <td></td>
            <td class="mark orange_cell single"></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td class="dark"></td>
            <td class="mark blank"></td>
            <td class="dark single" title="Single content cell"></td>
            <td></td>
            <td></td>
            <td></td>
            <td class="dark single" title="Single content cell"></td>
        </tr>
        <!-- empty row -->
        <tr>
            <td colspan="7" class="mark blank"></td>
        </tr>
        <!-- message row -->
        <tr>
            <td class="mark blank"></td>
            <td class="mark blank"></td>
            <td id="message" colspan="5" class="mark dark2">Set green and orange elements to the green and orange cells,
                respectively.
            </td>
        </tr>
    </table>
</div>
</body>
</html>