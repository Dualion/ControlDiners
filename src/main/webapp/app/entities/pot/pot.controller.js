(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('PotController', PotController);

    PotController.$inject = ['$scope', '$state', '$filter', 'Pot', 'ParseLinks', 'AlertService', 'pagingParams', 'paginationConstants'];

    function PotController ($scope, $state, $filter, Pot, ParseLinks, AlertService, pagingParams, paginationConstants) {
        var vm = this;
        
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = 100;
        vm.series = ['Diners Totals'];
        
        loadAll();

        function loadAll () {
            Pot.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.labels = [];
                vm.values = [[]];
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.pots = data;
                vm.page = pagingParams.page;
                
                for (var i=data.length-1; i>=0; i--) {
                	vm.values[0].push(data[i].dinersTotals);
                	var dataFormatada = $filter('date')(data[i].data, 'short');
                	vm.labels.push(dataFormatada);
            	}
               
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
