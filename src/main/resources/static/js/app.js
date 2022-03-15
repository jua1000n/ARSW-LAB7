app = (function () {
    let aut;
    let name;
    let listAuthor;
    let pointDraw;
    const mock = apiclient;
    let x = document.getElementById("canvast");
    let c = document.getElementById("myCanvas");
    let ctx = c.getContext("2d");
    //const mock = apimock;

    const putDate = () => {
        let blueprint = {}
        blueprint.author = aut;
        blueprint.name = name;
        blueprint.points = pointDraw;
        let putPromise = $.ajax({
            url: "/blueprints/" + aut + "/" + name,
            type: 'PUT',
            data: JSON.stringify(blueprint),
            contentType: "application/json"
        });
        putPromise.then(
            function () {
                console.log("OK");
                getNameAuthorBlueprint();
            }, function () {
                console.log("ERROR");
            }
        );
    }

    const drawMouse = () => {

        console.info('initialized');

        if(window.PointerEvent) {
            c.addEventListener("pointerdown", function(event){
                //console.info('pointerdown at '+event.pageX+','+event.pageY);
                var prueba = {};
                let correcCanvas = getOffset(c);
                prueba.x=event.pageX-correcCanvas.left;
                prueba.y=event.pageY-correcCanvas.top;
                pointDraw.push(prueba);
                draw(pointDraw);
            });
        }else {
            c.addEventListener("mousedown", function(event){
                //alert('mousedown at '+event.clientX+','+event.clientY);
                var prueba = {};
                let correcCanvas = getOffset(c);
                prueba.x=event.pageX-correcCanvas.left;
                prueba.y=event.pageY-correcCanvas.top;
                pointDraw.push(prueba);
                draw(pointDraw);
                }
            );
        }
    }

    const getOffset = (obj) => {
        var offsetLeft = 0;
        var offsetTop = 0;
        do {
            if (!isNaN(obj.offsetLeft)) {
                offsetLeft += obj.offsetLeft;
            }
            if (!isNaN(obj.offsetTop)) {
                offsetTop += obj.offsetTop;
            }
        } while(obj = obj.offsetParent );
        return {left: offsetLeft, top: offsetTop};
    }

    const callbackBlue = (listCall) => {
        console.log("ingreso2");
        const list = listCall.map(blueprint => {
           return {
               name: blueprint.name,

               points: blueprint.points.length
           }
        });

        var x = document.getElementById("resultt");
        x.querySelector(".example").innerHTML = (aut + "'s blueprints");

        $("#table-tbody").empty();
        listAuthor = list;

        list.map(blueprint => {

            const {name, points} = blueprint;
            const col = document.createElement('tr');
            //getNameAuthorNameBlueprint(name);

            col.innerHTML = `
                <td style="padding: 10px; text-align: left; border: 1px solid #cdcdcd;">${name}</td>
                <td style="padding: 10px; text-align: left; border: 1px solid #cdcdcd;">${points}</td>
                <td style="padding: 10px; text-align: left; border: 1px solid #cdcdcd;"><button onclick="app.getNameAuthorNameBlueprint('${name}')" >Open</button></td>
                `
            $("#table-tbody").append(col);
        });
    }

    const getNameAuthorBlueprint =  () => {
        aut = document.getElementsByName("author")[0].value;
        console.log("ingreso");
        if(aut === "") {
            alert("No ingreso ningun author")
        }else {
            mock.getBlueprintsByAuthor(aut, callbackBlue);
        }
    }

    const draw = (point) => {
        ctx.clearRect(0, 0, c.width, c.height);
        c.width = c.width;

        if(point.length>0) {

            console.log(point);
            ctx.moveTo(point[0].x,point[0].y);
            for(var i =1; i< point.length; i++) {
                ctx.lineTo(point[i].x,point[i].y);
            }

            ctx.stroke();
        }
    }

    const getNameAuthorNameBlueprint = (authorName) => {
        drawMouse();
        mock.getBlueprintsByNameAndAuthor(aut, authorName, (poin) => {
            x.querySelector(".excanvas").innerHTML = ("Current blueprint: " +authorName);
            let point;
            if(poin.points === undefined) {
                point = poin;

            } else {
                point = poin.points;
            }
            pointDraw = point;
            name = authorName;
            draw(point);
        });
    }

    const setNameAuthor = (newName) => {
        name = newName;
    }

    return{
        drawMouse: drawMouse,
        getNameAuthorBlueprint: getNameAuthorBlueprint,
        getNameAuthorNameBlueprint: getNameAuthorNameBlueprint,
        putDate: putDate
    }

})();