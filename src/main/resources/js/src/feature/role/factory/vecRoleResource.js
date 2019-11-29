vecchioApp.factory('vecRoleResource', [ '$resource', 'vecAPIServerService', function($resource, vecAPIServerService) {
	return $resource(vecAPIServerService.get().concat('/v2/role/:id'), {
		id : '@id'
	}, {
		update : {
			method : 'PUT'
		}
	});
} ]);
