vecchioApp.controller('VecModalSelectGroupListCtrl', [
	"$uibModalInstance",
	"username",
	"vecGroupResource",
	function ($uibModalInstance, username, vecGroupResource) {
		var $ctrl = this;
		$ctrl.username = username;
		$ctrl.checkAll = false;
		$ctrl.vecStateObjects = [];
		$ctrl.vecObjects = [];
		$ctrl.vecGroups = vecGroupResource.query({}, function () {
			angular.forEach($ctrl.vecGroups, function (vecGroup, key) {
				$ctrl.vecStateObjects[vecGroup.id] = false;
				$ctrl.vecObjects[vecGroup.id] = vecGroup;
			});
		});

		$ctrl.itemSelected = false;
		$ctrl.ok = function () {			
			var objects = [];
			console.log("ok");
            for (var stateKey in $ctrl.vecStateObjects) {
				console.log($ctrl.vecStateObjects[stateKey]);
                if ($ctrl.vecStateObjects[stateKey] === true) {
					console.log("Add");
					objects.puvec($ctrl.vecObjects[stateKey]);
                }
			}
			console.log("Valida");
			angular.forEach(objects, function (vecGroup, key) {
				console.log(vecGroup.name)
			});
			console.log("Termina");
			$uibModalInstance.close(objects);
		};

		$ctrl.cancel = function () {
			$ctrl.removeInstance = false;
			$uibModalInstance.dismiss('cancel');
		};

		$ctrl.checkSomeItemSelected = function () {
			$ctrl.itemSelected = false;
			for (var stateKey in $ctrl.vecStateObjects) {
				if ($ctrl.vecStateObjects[stateKey]) {
					$ctrl.itemSelected = true;
				}
			}
			console.log("checkSomeItemSelected");
			for (var stateKey in $ctrl.vecStateObjects) {
				console.log($ctrl.vecStateObjects[stateKey]);
            }
		}
		$ctrl.selectEverything = function () {
			if ($ctrl.checkAll) {
				for (var stateKey in $ctrl.vecStateObjects) {
					$ctrl.vecStateObjects[stateKey] = true;
				}
			}
			else {
				for (var stateKey in $ctrl.vecStateObjects) {
					$ctrl.vecStateObjects[stateKey] = false;
				}
			}
			$ctrl.checkSomeItemSelected();
		}
	}]);
