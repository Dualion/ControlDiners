(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('QuantitatDialogController', QuantitatDialogController);

    QuantitatDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Quantitat'];

    function QuantitatDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Quantitat) {
        var vm = this;

        vm.quantitat = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.quantitat.id !== null) {
                Quantitat.update(vm.quantitat, onSaveSuccess, onSaveError);
            } else {
                Quantitat.save(vm.quantitat, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('controlDinersApp:quantitatUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
