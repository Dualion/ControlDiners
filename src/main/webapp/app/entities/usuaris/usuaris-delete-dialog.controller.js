(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('UsuarisDeleteController',UsuarisDeleteController);

    UsuarisDeleteController.$inject = ['$uibModalInstance', 'entity', 'Usuaris'];

    function UsuarisDeleteController($uibModalInstance, entity, Usuaris) {
        var vm = this;

        vm.usuaris = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Usuaris.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
