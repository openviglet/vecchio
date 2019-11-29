vecchioApp.config([
	'$stateProvider', '$urlRouterProvider', '$translateProvider', 
	function ($stateProvider, $urlRouterProvider, $translateProvider) {
		$translateProvider.useSanitizeValueStrategy('escaped');
		$translateProvider
			.translations(
				'en',
				{

					USERNAME: "Username",
					FIRST_NAME: "First Name",
					LAST_NAME: "Last Name",
					PASSWORD: "Password",
					SETTINGS_ACCOUNT_TITLE: "Account",
					SETTINGS_ACCOUNT_SUBTITLE: "Change your basic account and language settings.",
					SETTINGS_SAVE_CHANGES: "Save Changes"
				});
		$translateProvider
			.translations(
				'pt',
				{
					USERNAME: "Nome do Usuário",
					FIRST_NAME: "Nome",
					LAST_NAME: "Sobrenome",
					PASSWORD: "Senha",
					SETTINGS_ACCOUNT_TITLE: "Conta",
					SETTINGS_ACCOUNT_SUBTITLE: "Altere suas configurações básicas da conta e de idioma.",
					SETTINGS_SAVE_CHANGES: "Salvar Alterações"
				});
		$translateProvider.fallbackLanguage('en');

		$urlRouterProvider.otherwise('/dashboard');
		$stateProvider
			.state('dashboard', {
				url: '/dashboard',
				templateUrl: '/template/dashboard/dashboard.html',
				controller: 'VecDashboardCtrl',
				data: { pageTitle: 'Dashboard | Viglet Vecchio' }
			})
			.state('organization', {
				url: '/org',
				templateUrl: '/template/organization/organization.html',
				data: { pageTitle: 'Organization | Viglet Vecchio' }
			})
			.state('mapping', {
				url: '/mapping',
				templateUrl: '/template/mapping/mapping.html',
				controller: 'VecMappingCtrl',
				data: { pageTitle: 'Mapping | Viglet Vecchio' }
			})
			.state('mapping-new', {
				url: '/mapping/new',
				templateUrl: '/template/mapping/mapping-item.html',
				controller: 'VecMappingNewCtrl',
				data: { pageTitle: 'New Mapping | Viglet Vecchio' }
			})
			.state('mapping-edit', {
				url: '/mapping/:mappingId',
				templateUrl: '/template/mapping/mapping-item.html',
				controller: 'VecMappingEditCtrl',
				data: { pageTitle: 'Edit Mapping | Viglet Vecchio' }
			})
			.state('organization.user', {
				url: '/user',
				templateUrl: '/template/user/user.html',
				controller: 'VecUserCtrl',
				data: { pageTitle: 'Users | Viglet Vecchio' }
			})
			.state('organization.user-new', {
				url: '/user/new',
				templateUrl: '/template/user/user-item.html',
				controller: 'VecUserNewCtrl',
				data: { pageTitle: 'New User | Viglet Vecchio' }
			})
			.state('organization.user-edit', {
				url: '/user/:userId',
				templateUrl: '/template/user/user-item.html',
				controller: 'VecUserEditCtrl',
				data: { pageTitle: 'Edit User | Viglet Vecchio' }
			})
			.state('organization.role', {
				url: '/role',
				templateUrl: '/template/role/role.html',
				controller: 'VecRoleCtrl',
				data: { pageTitle: 'Roles | Viglet Vecchio' }
			})
			.state('organization.role-new', {
				url: '/role/new',
				templateUrl: '/template/role/role-item.html',
				controller: 'VecRoleNewCtrl',
				data: { pageTitle: 'New Role | Viglet Vecchio' }
			})
			.state('organization.role-edit', {
				url: '/role/:roleId',
				templateUrl: '/template/role/role-item.html',
				controller: 'VecRoleEditCtrl',
				data: { pageTitle: 'Edit Role | Viglet Vecchio' }
			})
			.state('organization.group', {
				url: '/group',
				templateUrl: '/template/group/group.html',
				controller: 'VecGroupCtrl',
				data: { pageTitle: 'Groups | Viglet Vecchio' }
			})
			.state('organization.group-new', {
				url: '/group/new',
				templateUrl: '/template/group/group-item.html',
				controller: 'VecGroupNewCtrl',
				data: { pageTitle: 'New Group | Viglet Vecchio' }
			})
			.state('organization.group-edit', {
				url: '/group/:groupId',
				templateUrl: '/template/group/group-item.html',
				controller: 'VecGroupEditCtrl',
				data: { pageTitle: 'Edit Group | Viglet Vecchio' }
			})
			.state('app', {
				url: '/app',
				templateUrl: '/template/app/app.html',
				controller: 'VecAppCtrl',
				data: { pageTitle: 'Apps | Viglet Vecchio' }
			})
			.state('app-new', {
				url: '/app/new',
				templateUrl: '/template/app/app-item.html',
				controller: 'VecAppNewCtrl',
				data: { pageTitle: 'New App | Viglet Vecchio', saveButton: 'Save' }
			})
			.state('app-edit', {
				url: '/app/:appId',
				templateUrl: '/template/app/app-item.html',
				controller: 'VecAppEditCtrl',
				data: { pageTitle: 'Edit App | Viglet Vecchio', saveButton: 'Update Settings' }
			})
			.state('app-edit.keys', {
				url: '/keys',
				templateUrl: '/template/app/app-item-keys.html',
				controller: 'VecAppEditCtrl',
				data: { pageTitle: 'Edit App Keys | Viglet Vecchio' }
			});

	}]);