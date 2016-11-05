(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .service('ExtreurePotService', ExtreurePotService);

    ExtreurePotService.$inject = ['$uibModal'];

    function ExtreurePotService ($uibModal) {

        this.open = function () {
            return $uibModal.open({
                animation: true,
                templateUrl: 'app/services/pot/extreure-pot.html',
                controller: 'ExtreurePotController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'sm',
                resolve: {
                    entity: function () {
                        return {
                            diners: null
                        };
                    },
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pot');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            }).result;
        }
    }
})();
