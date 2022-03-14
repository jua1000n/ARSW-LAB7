
apiclient=(function(){
    return{
        getBlueprintsByAuthor: (author,callback)=>{
            const date = $.get({url: "/blueprints/"+ author, contentType: "application/json"});
            date.then(function (json) {
                let temp = JSON.parse(json);
                return callback(temp);
            });
        },

        getBlueprintsByNameAndAuthor:(author,name,callback)=>{
            const date = $.get({url: "/blueprints/"+ author + "/" + name, contentType: "application/json"});
            date.then(function (json) {
                let temp = JSON.parse(json);
                callback(temp);
            });
        }
    }
})();