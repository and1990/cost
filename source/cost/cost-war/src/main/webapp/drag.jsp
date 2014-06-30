<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <?
	$keywords = "Drag and drop script,DHTML drag and drop,drag,drop";
	@include($_SERVER['DOCUMENT_ROOT']."/config/metatags.inc");
	?>
    <style type="text/css">
        #mainContainer {
            width: 600px;
            margin: 0 auto;
            margin-top: 10px;
            border: 1px solid #000;
            padding: 2px;
        }

        #leftColumn {
            width: 300px;
            float: left;
            border: 1px solid #000;
            background-color: #E2EBED;
            padding: 3px;
            height: 400px;
        }

        #rightColumn {
            width: 200px;
            float: right;
            margin: 2px;
            height: 400px;
        }

        .dragableBox {
            width: 100px;
            height: 40px;
            border: 1px solid #000;
            background-color: #FFF;
            margin-bottom: 5px;
            padding: 10px;
            font-weight: bold;
            text-align: center;
        }

        .dropBox {
            width: 190px;
            border: 1px solid #000;
            background-color: #E2EBED;
            height: 400px;
            margin-bottom: 10px;
            padding: 3px;
            overflow: auto;
        }

        a {
            color: #F00;
        }

        .clear {
            clear: both;
        }

        img {
            border: 0px;
        }
    </style>
    <script type="text/javascript" src="js/drag-drop-custom.js"></script>
</head>
<body>
<div id="mainContainer">
    <div id="leftColumn">
        <div id="dropContent">
            <div class="dragableBox" id="box1">CAT</div>
            <div class="dragableBox" id="box2">DOG</div>
            <div class="dragableBox" id="box3">HORSE</div>
            <div class="dragableBox" id="box4">TIGER</div>
        </div>
    </div>

    <div id="rightColumn">
        <div id="dropBox" class="dropBox">
            <div></div>
            <table border="1">
                <tr>
                    <td id="dropContent2">row 1, cell 1</td>
                    <td>row 1, cell 2</td>
                </tr>
                <tr>
                    <td>row 2, cell 1</td>
                    <td>row 2, cell 2</td>
                </tr>
            </table>
        </div>
    </div>

</div>


<script type="text/javascript">
    function dropItems(idOfDraggedItem, targetId, x, y) {
        if (targetId == 'dropBox') {
            var obj = document.getElementById(idOfDraggedItem);
            if (obj.parentNode.id == 'dropContent2')
                return;
            else
                document.getElementById('dropContent2').appendChild(obj);
        }
        if (targetId == 'leftColumn') {
            var obj = document.getElementById(idOfDraggedItem);
            if (obj.parentNode.id == 'dropContent')
                return;
            else
                document.getElementById('dropContent').appendChild(obj);
        }

    }

    function onDragFunction(cloneId, origId) {
        self.status = 'Started dragging element with id ' + cloneId;

        var obj = document.getElementById(cloneId);
        obj.style.border = '4px solid #F00';
    }


    var dragDropObj = new DHTMLgoodies_dragDrop();
    dragDropObj.addSource('box1', true, true, true, false, 'onDragFunction');
    dragDropObj.addSource('box2', true, true, true, false, 'onDragFunction');
    dragDropObj.addSource('box3', true, true, true, false, 'onDragFunction');
    dragDropObj.addSource('box4', true, true, true, false, 'onDragFunction');
    dragDropObj.addTarget('dropBox', 'dropItems');
    dragDropObj.addTarget('leftColumn', 'dropItems');
    dragDropObj.init();
</script>

<br><br>
</body>
</html>
