//@author hcadavid

apimock=(function(){

	var mockdata=[];

	mockdata["johnconnor"]=	[{author:"johnconnor","points":[{"x":150,"y":120}],"name":"house"},
	 {author:"johnconnor","points":[{"x":340,"y":240}],"name":"gear"},
		{author:"johnconnor","points":[{"x":340,"y":240}],"name":"mount"}];
	mockdata["maryweyland"]=[{author:"maryweyland","points":[{"x":140,"y":140}],"name":"house2"},
	 {author:"maryweyland","points":[{"x":140,"y":140}],"name":"gear2"}];
	mockdata["bad"]=[{author:"bad","points":[{"x":150,"y":10},{"x":145,"y":112},{"x":15,"y":11}],"name":"blueprint01"},
	 {author:"bad","points":[{"x":150,"y":10},{"x":145,"y":112},{"x":15,"y":11}],"name":"gear"}];
	mockdata["arturo"]=[{author:"arturo","points":[{"x":115,"y":115}],"name":"house2"},
	 {author:"arturo","points":[{"x":140,"y":140},{"x":115,"y":115}],"name":"gear2"}];
	mockdata["other"]=[{author:"other","points":[{"x":150,"y":120},{"x":215,"y":115},{"x":115,"y":115}],"name":"house"},
	 {author:"other","points":[{"x":340,"y":240}],"name":"gear"}];
	mockdata["carlos"]=[{author:"carlos","points":[{"x":115,"y":115},{"x":400,"y":100}],"name":"house2"},
		{author:"carlos","points":[{"x":0,"y":0},{"x":200,"y":100},{"x":300,"y":100}],"name":"gear2"}];


	return {
		getBlueprintsByAuthor:function(authname,callback){
			callback(
				mockdata[authname]
			);
		},

		getBlueprintsByNameAndAuthor:function(authname,bpname,callback){

			callback(
				mockdata[authname].find(function(e){return e.name===bpname})
			);
		}
	}	

})();

/*



Example of use:
var fun=function(list){
	console.info(list);
}

apimock.getBlueprintsByAuthor("johnconnor",fun);
apimock.getBlueprintsByNameAndAuthor("johnconnor","house",fun);*/