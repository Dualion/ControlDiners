(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('usuaris-proces', {
            parent: 'entity',
            url: '/usuaris-proces?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'controlDinersApp.usuarisProces.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/usuaris-proces/usuaris-proces.html',
                    controller: 'UsuarisProcesController',
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
                    $translatePartialLoader.addPart('usuarisProces');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('usuaris-proces-detail', {
            parent: 'entity',
            url: '/usuaris-proces/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'controlDinersApp.usuarisProces.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/usuaris-proces/usuaris-proces-detail.html',
                    controller: 'UsuarisProcesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('usuarisProces');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UsuarisProces', function($stateParams, UsuarisProces) {
                    return UsuarisProces.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'usuaris-proces',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('usuaris-proces-detail.edit', {
            parent: 'usuaris-proces-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usuaris-proces/usuaris-proces-dialog.html',
                    controller: 'UsuarisProcesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UsuarisProces', function(UsuarisProces) {
                            return UsuarisProces.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('usuaris-proces.new', {
            parent: 'usuaris-proces',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usuaris-proces/usuaris-proces-dialog.html',
                    controller: 'UsuarisProcesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                diners: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('usuaris-proces', null, { reload: 'usuaris-proces' });
                }, function() {
                    $state.go('usuaris-proces');
                });
            }]
        })
        .state('usuaris-proces.edit', {
            parent: 'usuaris-proces',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usuaris-proces/usuaris-proces-dialog.html',
                    controller: 'UsuarisProcesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UsuarisProces', function(UsuarisProces) {
                            return UsuarisProces.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('usuaris-proces', null, { reload: 'usuaris-proces' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('usuaris-proces.delete', {
            parent: 'usuaris-proces',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usuaris-proces/usuaris-proces-delete-dialog.html',
                    controller: 'UsuarisProcesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UsuarisProces', function(UsuarisProces) {
                            return UsuarisProces.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('usuaris-proces', null, { reload: 'usuaris-proces' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
