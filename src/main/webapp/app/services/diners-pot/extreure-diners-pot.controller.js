(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('ExtreureDinersPotController', ExtreureDinersPotController);

    ExtreureDinersPotController.$inject = ['$timeout', '$uibModalInstance', 'entity', 'DinersPot'];

    function ExtreureDinersPotController ($timeout, $uibModalInstance, entity, DinersPot) {
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
            DinersPot.extreure(vm.dinersPot, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
