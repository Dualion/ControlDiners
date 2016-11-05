'use strict';

describe('Controller Tests', function() {

    describe('UsuarisProces Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUsuarisProces, MockProces, MockUsuaris;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUsuarisProces = jasmine.createSpy('MockUsuarisProces');
            MockProces = jasmine.createSpy('MockProces');
            MockUsuaris = jasmine.createSpy('MockUsuaris');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UsuarisProces': MockUsuarisProces,
                'Proces': MockProces,
                'Usuaris': MockUsuaris
            };
            createController = function() {
                $injector.get('$controller')("UsuarisProcesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'controlDinersApp:usuarisProcesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
