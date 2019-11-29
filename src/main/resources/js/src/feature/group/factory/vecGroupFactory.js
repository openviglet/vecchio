vecchioApp.factory('vecGroupFactory', [
	'$uibModal', 'vecGroupResource', 'Notification', '$state',
	function ($uibModal, vecGroupResource, Notification, $state) {
		return {
			delete: function (vecGroup) {
				var modalInstance = this.modalDelete(vecGroup);
				modalInstance.result.then(function (removeInstance) {
					deletedMessage = 'The ' + vecGroup.name + ' was deleted.';

					vecGroupResource
						.delete({
							id: vecGroup.id
						}, function () {
							Notification.error(deletedMessage);
							$state.go('admin.group', {}, { reload: true });
						});
				}, function () {
					// Selected NO
				});
			},
			save: function (vecGroup) {
				if (vecGroup.id > 0) {
					var updateMessage = 'The ' + vecGroup.name + ' was saved.';
					vecGroup.$update(function () {
						Notification.warning(updateMessage);
						$state.go('admin.group');
					});
				} else {
					var saveMessage = 'The ' + vecGroup.name + ' was updated.';
					delete vecGroup.id;
					vecGroupResource.save(vecGroup, function (response) {
						Notification.warning(saveMessage);
						$state.go('admin.group');
					});
				}
			}, addUsers: function (vecGroup) {
				var modalInstance = this.modalSelectUser(vecGroup);
				modalInstance.result.then(function (vecUsers) {
					if (vecGroup.vecUsers != null) {
						angular.forEach(vecUsers, function (vecUser, key) {
							console.log(vecUser.username);							
							vecGroup.vecUsers.puvec(vecUser);
						});
					}
					else {
						vecGroup.vecUsers = vecUsers;
					}
				}, function () {
					// Selected NO
				});
			},
			modalDelete: function (vecGroup) {
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
							return vecGroup.name;
						}
					}
				});
			},
			modalSelectUser: function (vecGroup) {
				var $ctrl = this;
				return $uibModal.open({
					animation: true
					, ariaLabelledBy: 'modal-title'
					, ariaDescribedBy: 'modal-body'
					, templateUrl: 'template/user/user-select-dialog.html'
					, controller: 'VecModalSelectUserListCtrl'
					, controllerAs: '$ctrl'
					, size: null
					, appendTo: undefined
					, resolve: {
						groupId: function () {
							return vecGroup.id;
						}
					}
				});
			}
		}
	}]);
