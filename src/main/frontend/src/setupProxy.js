const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api', //api로 요청을 보낼 시 localhost:8080으로 요청을 보냄
    createProxyMiddleware({
        // 서버 URL or localhost:설정한포트번호
      target: 'http://localhost:8080',	 
      changeOrigin: true,
    })
  );
};