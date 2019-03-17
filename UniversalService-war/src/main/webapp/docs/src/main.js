/**
 * Function loading Swagger UI
 */
window.loadSwagger = function () {
	// firebase login
	const
	firebaseLoginUI = new firebaseui.auth.AuthUI(firebase.auth());
	const
	uiConfig = {
		callbacks : {
			signInSuccessWithAuthResult : function(authResult, redirectUrl) {
				// User successfully signed in.
				// Return type determines whether we continue the redirect
				// automatically
				// or whether we leave that to developer to handle.
				return true;
			},
			uiShown : function() {
				// The widget is rendered.
				// Hide the loader.
				document.getElementById('loader').style.display = 'none';
			}
		},
		// Will use popup for IDP Providers sign-in flow instead of the default,
		// redirect.
		signInFlow : 'popup',
		signInSuccessUrl : '/docs/',
		signInOptions : [ {
			provider : firebase.auth.GoogleAuthProvider.PROVIDER_ID,
			customParameters : {
				// Forces account selection even when one account
				// is available.
				prompt : 'select_account'
			}
		} ],
		// Terms of service url.
		tosUrl : '/'
	};
	// The start method will wait until the DOM is loaded.
	firebaseLoginUI.start('#firebaseui-auth-container', uiConfig);

	firebase
			.auth()
			.onAuthStateChanged(
					function(user) {
						// swagger ui
						console.log('[Swagger] render swagger ui');
						const
						ui = SwaggerUIBundle({
							url: "/openapi.json",
							//url : '/openapilocal.json',
							validatorUrl : 'https://online.swagger.io/validator',
							dom_id : '#swagger-ui',
							presets : [
									SwaggerUIBundle.presets.apis,
									SwaggerUIStandalonePreset ],
							plugins : [ SwaggerUIBundle.plugins.DownloadUrl ],
							layout : 'StandaloneLayout'
						});
						
						// check if user is authenticated
						if (user) {
							user.getIdToken()
									.then(
											function(accessToken) {
												ui.authActions.authorize({bearer: {name: "bearer", schema: {type: "apiKey", in: "header", name: "Authorization", description: ""}, value: "Bearer " + accessToken}});
											});
						} else {
							ui.authActions.logout(["api_key"]);
						}
						window.ui = ui;
					});
}

