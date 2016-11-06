(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('diners-pot', {
            parent: 'entity',
            url: '/diners-pot?page&sort&search',
            data: {
                pageTitle: 'controlDinersApp.dinersPot.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/diners-pot/diners-pots.html',
                    controller: 'DinersPotController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,desc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dinersPot');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('diners-pot-detail', {
            parent: 'entity',
            url: '/diners-pot/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'controlDinersApp.dinersPot.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/diners-pot/diners-pot-detail.html',
                    controller: 'DinersPotDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dinersPot');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DinersPot', function($stateParams, DinersPot) {
                    return DinersPot.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'diners-pot',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('diners-pot-detail.edit', {
            parent: 'diners-pot-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/diners-pot/diners-pot-dialog.html',
                    controller: 'DinersPotDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DinersPot', function(DinersPot) {
                            return DinersPot.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('diners-pot.new', {
            parent: 'diners-pot',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/diners-pot/diners-pot-dialog.html',
                    controller: 'DinersPotDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dinersTotals: null,
                                data: null,
                                descripcio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('diners-pot', null, { reload: 'diners-pot' });
                }, function() {
                    $state.go('diners-pot');
                });
            }]
        })
        .state('diners-pot.edit', {
            parent: 'diners-pot',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/diners-pot/diners-pot-dialog.html',
                    controller: 'DinersPotDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DinersPot', function(DinersPot) {
                            return DinersPot.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('diners-pot', null, { reload: 'diners-pot' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
