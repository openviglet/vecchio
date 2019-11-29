vecchioApp.factory('vecMappingResource', [ '$resource', 'vecAPIServerService', function($resource, vecAPIServerService) {
	return $resource(vecAPIServerService.get().concat('/v2/mapping/:id'), {
		id : '@id'
	}, {
		update : {
			method : 'PUT'
		}
	});
} ]);
