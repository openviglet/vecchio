vecchioApp.factory('vecMappingFactory', [
	'$uibModal', 'vecMappingResource', 'Notification', '$filter',
	function ($uibModal, vecMappingResource, Notification, $filter) {
		return {
			deleteFromList: function (mapping, mappings) {
				var modalInstance = this.modalDelete(mapping);
				modalInstance.result.then(function (removeInstance) {
					deletedMessage = 'The Mapping was deleted.';
					vecMappingResource
						.delete({
							id: mapping.id
						}, function () {
							// filter the array
							var foundItem = $filter('filter')(mappings, { id: mapping.id }, true)[0];
							// get the index
							var index = mappings.indexOf(foundItem);
							// remove the item from array
							mappings.splice(index, 1);
							Notification.error(deletedMessage);
						});
				}, function () {
					// Selected NO
				});
			},			
			modalDelete: function (mapping) {
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
							return mapping;
						}
					}
				});
			}
		}
	}]);