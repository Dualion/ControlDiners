(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('UsuarisProcesDialogController', UsuarisProcesDialogController);

    UsuarisProcesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UsuarisProces', 'Proces', 'Usuaris'];

    function UsuarisProcesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UsuarisProces, Proces, Usuaris) {
        var vm = this;

        vm.usuarisProces = entity;
        vm.clear = clear;
        vm.save = save;
        vm.proces = Proces.query();
        vm.usuarises = Usuaris.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.usuarisProces.id !== null) {
                UsuarisProces.update(vm.usuarisProces, onSaveSuccess, onSaveError);
            } else {
                UsuarisProces.save(vm.usuarisProces, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('controlDinersApp:usuarisProcesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
