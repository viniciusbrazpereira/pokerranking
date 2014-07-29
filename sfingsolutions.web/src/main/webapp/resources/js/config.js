CKEDITOR.editorConfig = function( config ) {	
    config.language = 'pt'; 
    config.filebrowserBrowseUrl = 'image_browser.jsf';
    config.filebrowserImageUploadUrl = 'upload';
    
    config.toolbar = 'MyToolbar';    
	config.toolbar_MyToolbar =
	[
		{ name: 'document', items : [ 'Preview','Maximize' ] },
		{ name: 'links', items : [ 'Link','Unlink','Anchor' ] },
		{ name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
		{ name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','Scayt' ] },
		{ name: 'insert', items : [ 'Image','Table','HorizontalRule','SpecialChar'] }, '/',
		{ name: 'styles', items : [ 'Styles','Format' ] },
		{ name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
		{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote' ] },
		{ name: 'tools', items : [ 'Source'] }
	];
    
    config.saveFunction = function(data) {
		alert(data);
	};
};