vecchioApp.factory('vecAppFactory', [
	'$uibModal', 'vecAppResource', 'Notification', '$filter',
	function ($uibModal, vecAppResource, Notification, $filter) {
		return {
			deleteFromList: function (app, apps) {
				var modalInstance = this.modalDelete(app, app.name + " App");
				modalInstance.result.then(function (removeInstance) {
					deletedMessage = 'The ' +  app.name +' App was deleted.';
					vecAppResource
						.delete({
							id: app.id
						}, function () {
							// filter the array
							var foundItem = $filter('filter')(apps, { id: app.id }, true)[0];
							// get the index
							var index = apps.indexOf(foundItem);
							// remove the item from array
							apps.splice(index, 1);
							Notification.error(deletedMessage);
						});
				}, function () {
					// Selected NO
				});
			},			
			modalDelete: function (app, appTitle) {
	
				var $ctrl = this;
				return $uibModal.open({
					animation: true
					, ariaLabelledBy: 'modal-title'
					, ariaDescribedBy: 'modal-body'
					, templateUrl: 'template/modal/vecDeleteObject.html'
					, controller: 'VecModalDeleteObjectCtrl'
					, controllerAs: '$ctrl'
					, size: null
					, appendTo: undefined
					, resolve: {
						vecObject: function () {
							return app;
						},
						vecObjectTitle: function () {
							return appTitle;
						}
					}
				});
			}
		}
	}]);