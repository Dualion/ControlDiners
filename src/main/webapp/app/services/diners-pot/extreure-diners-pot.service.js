(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .service('ExtreureDinersPotService', ExtreureDinersPotService);

    ExtreureDinersPotService.$inject = ['$uibModal'];

    function ExtreureDinersPotService ($uibModal) {

        this.open = function () {
            return $uibModal.open({
                animation: true,
                templateUrl: 'app/services/pot/extreure-diners-pot.html',
                controller: 'ExtreureDinersPotController',
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
                        $translatePartialLoader.addPart('dinerspot');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            }).result;
        }
    }
})();
