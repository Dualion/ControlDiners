(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('NovaQuantitatController', NovaQuantitatController);

    NovaQuantitatController.$inject = ['$timeout', '$uibModalInstance', 'entity', 'Quantitat'];

    function NovaQuantitatController ($timeout, $uibModalInstance, entity, Quantitat) {
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
            Quantitat.save(vm.quantitat, onSaveSuccess, onSaveError);
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
