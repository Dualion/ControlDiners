(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('quantitat', {
            parent: 'entity',
            url: '/quantitat?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'controlDinersApp.quantitat.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/quantitat/quantitats.html',
                    controller: 'QuantitatController',
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
                    $translatePartialLoader.addPart('quantitat');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('quantitat-detail', {
            parent: 'entity',
            url: '/quantitat/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'controlDinersApp.quantitat.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/quantitat/quantitat-detail.html',
                    controller: 'QuantitatDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('quantitat');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Quantitat', function($stateParams, Quantitat) {
                    return Quantitat.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'quantitat',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('quantitat-detail.edit', {
            parent: 'quantitat-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantitat/quantitat-dialog.html',
                    controller: 'QuantitatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Quantitat', function(Quantitat) {
                            return Quantitat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('quantitat.new', {
            parent: 'quantitat',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantitat/quantitat-dialog.html',
                    controller: 'QuantitatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                diners: null,
                                actiu: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('quantitat', null, { reload: 'quantitat' });
                }, function() {
                    $state.go('quantitat');
                });
            }]
        })
        .state('quantitat.edit', {
            parent: 'quantitat',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantitat/quantitat-dialog.html',
                    controller: 'QuantitatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Quantitat', function(Quantitat) {
                            return Quantitat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('quantitat', null, { reload: 'quantitat' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('quantitat.delete', {
            parent: 'quantitat',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/quantitat/quantitat-delete-dialog.html',
                    controller: 'QuantitatDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Quantitat', function(Quantitat) {
                            return Quantitat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('quantitat', null, { reload: 'quantitat' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
