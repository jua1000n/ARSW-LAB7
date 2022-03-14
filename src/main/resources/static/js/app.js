app = (function () {
    let aut;
    let name;
    let listAuthor;
    const mock = apiclient;
    //const mock = apimock;

    const callbackBlue = (listCall) => {
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
        if(aut === "") {
            alert("No ingreso ningun author")
        }else {
            mock.getBlueprintsByAuthor(aut, callbackBlue);
        }
    }

    const getNameAuthorNameBlueprint = (authorName) => {
        mock.getBlueprintsByNameAndAuthor(aut, authorName, (poin) => {
            let point;
            if(poin.points === undefined) {
                point = poin;

            } else {
                point = poin.points;
            }

            var x = document.getElementById("canvast");
            x.querySelector(".excanvas").innerHTML = ("Current blueprint: " +authorName);
            if(point.length>0) {
                var c = document.getElementById("myCanvas");
                var ctx = c.getContext("2d");

                ctx.clearRect(0, 0, c.width, c.height);
                c.width = c.width;

                ctx.moveTo(point[0].x,point[0].y);
                for(var i =1; i< point.length; i++) {
                    ctx.lineTo(point[i].x,point[i].y);
                }

                ctx.stroke();
            }
        });
    }

    const setNameAuthor = (newName) => {
        name = newName;
    }

    return{
        getNameAuthorBlueprint: getNameAuthorBlueprint,
        getNameAuthorNameBlueprint: getNameAuthorNameBlueprint
    }

})();