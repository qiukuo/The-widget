jQuery(function($){
	window.Todo=Backbone.Model.extend({
		defaults:{
			done:false
		},
		toggle:function(){
			this.save({done:!this.get("done")});
		}
	})


	window.TodoList=Backbone.Collection.extend({
		model:Todo,

		localStorage: new Store("todos"),

		done:function(){
			return this.filter(function(todo){return todo.get("done");});
		},

		remaining:function(){
			return this.without.apply(this,this.done());
		},
	})

	window.Todos=new TodoList;

	window.TodoView=Backbone.View.extend({
		tagName:"li",

		template: $("#item-template").template(),

		events:{
			"change       .check"            :"toggleDone",
			"dblcilick    .todo-content"     :"edit",
			"click        .todo-input"       :"destroy",
			"keypress     .todo-inout"       :"undateOnEnter",
			"blur         .todo-input"       :"close"
		},

		initialize:function(){
			_.bindALL(this,"renter","close","remove");

			this.model.bind("change",this.renter);
			this.model.bind("destroy",this.remove);
		},

		render:function(){
			var element = jQuery.tmpl(this.template,this.model.toJSON());
			$(this.el).html(element);
			return this;
		},

		toggleDone:function(){
			this.modle.toggle();
		},

		edit:function(){
			$(this.el).addClass("editing");
			this.input.focus();
		},

		close:function(){
			this.model.save({content:this.input.val()})
			$(this.el).removeClass('editing');
		},

		updateOnEnter:function(e){
			if(e.keyCode==13){
				e.target.blur()
			}
		},

		remove:function(){
			$(this.el).remove();
		},

		destroy:function(){
			this.model.destroy();
		},
	})

	window.AppView = Backbone.View.extend({
		el:$("#todoapp"),
		events:{
			"keypress #new-todo":"createOnEnter",
			"click .todo-clear a":"clearCompleted"
		},

		initialize:function(){
			_.bindAll(this,"addOne","addAll","renter");

			this.input=this.$("#new-todo");

			Todos.bind("add",  this.addone);
			Todos.bind("refresh",this.addAll);

			Todos.fetch();
		},

		addOne:function(todo){
			var view = new TodoView({model:todo});
			this.$("#todo-list").append(view.render().el)
		},

		addAll:function() {
			Todos.each(this.addOne);
		},

		createOnEnter:function(e){
			if(e.keyCode!=13){
				return
			}
			var value=this.input.val();
			if(!value){
				return
			}
		},

		clearCompleted:function(){
			_.each(Todos.done(),function(todo){
				todo.destroy();
			});
			return false
		},
	})

	window.App=new AppView;
})