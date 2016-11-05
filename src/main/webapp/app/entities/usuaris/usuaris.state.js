(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('usuaris', {
            parent: 'entity',
            url: '/usuaris?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'controlDinersApp.usuaris.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/usuaris/usuarises.html',
                    controller: 'UsuarisController',
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
                    $translatePartialLoader.addPart('usuaris');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('usuaris-detail', {
            parent: 'entity',
            url: '/usuaris/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'controlDinersApp.usuaris.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/usuaris/usuaris-detail.html',
                    controller: 'UsuarisDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('usuaris');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Usuaris', function($stateParams, Usuaris) {
                    return Usuaris.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'usuaris',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('usuaris-detail.edit', {
            parent: 'usuaris-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usuaris/usuaris-dialog.html',
                    controller: 'UsuarisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Usuaris', function(Usuaris) {
                            return Usuaris.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('usuaris.new', {
            parent: 'usuaris',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usuaris/usuaris-dialog.html',
                    controller: 'UsuarisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                email: null,
                                dataInici: null,
                                actiu: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('usuaris', null, { reload: 'usuaris' });
                }, function() {
                    $state.go('usuaris');
                });
            }]
        })
        .state('usuaris.edit', {
            parent: 'usuaris',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usuaris/usuaris-dialog.html',
                    controller: 'UsuarisDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Usuaris', function(Usuaris) {
                            return Usuaris.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('usuaris', null, { reload: 'usuaris' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('usuaris.delete', {
            parent: 'usuaris',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/usuaris/usuaris-delete-dialog.html',
                    controller: 'UsuarisDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Usuaris', function(Usuaris) {
                            return Usuaris.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('usuaris', null, { reload: 'usuaris' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
