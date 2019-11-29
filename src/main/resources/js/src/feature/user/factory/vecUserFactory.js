vecchioApp.factory('vecUserFactory', [
	'$uibModal', 'vecUserResource', 'Notification', '$state',
	function ($uibModal, vecUserResource, Notification, $state) {
		return {
			delete: function (vecUser) {
				var modalInstance = this.modalDelete(vecUser);
				modalInstance.result.then(function (removeInstance) {
					deletedMessage = 'The ' + vecUser.username + ' was deleted.';

					vecUserResource
						.delete({
							id: vecUser.username
						}, function () {
							Notification.error(deletedMessage);
							$state.go('admin.user', {}, { reload: true });
						});
				}, function () {
					// Selected NO
				});
			},
			save: function (vecUser, isNew) {
				if (!isNew) {
					var updateMessage = 'The ' + vecUser.username + ' was updated.';
					vecUser.$update(function () {
						Notification.warning(updateMessage);
						$state.go('admin.user');
					});
				} else {
					var saveMessage = 'The ' + vecUser.username + ' was saved.';
					vecUserResource.save(vecUser, function (response) {
						Notification.warning(saveMessage);
						isNew = false;
						$state.go('admin.user');
					});
				}
			},
			addGroups: function (vecUser) {
				var modalInstance = this.modalSelectGroup(vecUser);
				modalInstance.result.then(function (vecGroups) {
					if (vecUser.vecGroups != null) {
						angular.forEach(vecGroups, function (vecGroup, key) {
							console.log(vecGroup.name);
							vecUser.vecGroups.puvec(vecGroup);
						});						
						//vecUser.vecGroups.concat(vecGroups[0]);
					}
					else {
						vecUser.vecGroups = vecGroups;
					}
				}, function () {
					// Selected NO
				});
			}, modalDelete: function (vecUser) {
				var $ctrl = this;
				return $uibModal.open({
					animation: true
					, ariaLabelledBy: 'modal-title'
					, ariaDescribedBy: 'modal-body'
					, templateUrl: 'template/user/user-delete.html'
					, controller: 'VecModalDeleteUserCtrl'
					, controllerAs: '$ctrl'
					, size: null
					, appendTo: undefined
					, resolve: {
						title: function () {
							return vecUser.username;
						}
					}
				});
			},
			modalSelectGroup: function (vecUser) {
				var $ctrl = this;
				return $uibModal.open({
					animation: true
					, ariaLabelledBy: 'modal-title'
					, ariaDescribedBy: 'modal-body'
					, templateUrl: 'template/group/group-select-dialog.html'
					, controller: 'VecModalSelectGroupListCtrl'
					, controllerAs: '$ctrl'
					, size: null
					, appendTo: undefined
					, resolve: {
						username: function () {
							return vecUser.username;
						}
					}
				});
			}
		}
	}]);
