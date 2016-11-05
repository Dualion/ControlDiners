(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('proces', {
            parent: 'entity',
            url: '/proces?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'controlDinersApp.proces.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proces/proces.html',
                    controller: 'ProcesController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
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
                    $translatePartialLoader.addPart('proces');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('proces-detail', {
            parent: 'entity',
            url: '/proces/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'controlDinersApp.proces.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proces/proces-detail.html',
                    controller: 'ProcesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('proces');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Proces', function($stateParams, Proces) {
                    return Proces.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'proces',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('proces-detail.edit', {
            parent: 'proces-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proces/proces-dialog.html',
                    controller: 'ProcesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Proces', function(Proces) {
                            return Proces.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('proces.new', {
            parent: 'proces',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proces/proces-dialog.html',
                    controller: 'ProcesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dataInici: null,
                                estat: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('proces', null, { reload: 'proces' });
                }, function() {
                    $state.go('proces');
                });
            }]
        })
        .state('proces.edit', {
            parent: 'proces',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proces/proces-dialog.html',
                    controller: 'ProcesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Proces', function(Proces) {
                            return Proces.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proces', null, { reload: 'proces' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('proces.delete', {
            parent: 'proces',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proces/proces-delete-dialog.html',
                    controller: 'ProcesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Proces', function(Proces) {
                            return Proces.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proces', null, { reload: 'proces' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
