vecchioApp.controller('VecModalDeleteObjectCtrl', [
    "$uibModalInstance",
    "vecObject",
    "vecObjectTitle",
    function($uibModalInstance, vecObject, vecObjectTitle) {
        var $ctrl = this;
        $ctrl.removeInstance = false;
        $ctrl.vecObject = vecObject;

        $ctrl.objectName = vecObjectTitle;

        $ctrl.ok = function() {
            $ctrl.removeInstance = true;
            $uibModalInstance.close($ctrl.removeInstance);
        };

        $ctrl.cancel = function() {
            $ctrl.removeInstance = false;
            $uibModalInstance.dismiss('cancel');
        };
    } ]);
