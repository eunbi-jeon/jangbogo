const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = (app) => {
  app.use(
    createProxyMiddleware('/v1/search/shop.json', {
      target: 'https://openapi.naver.com',
      changeOrigin: true,
    }),
  );
  app.use(
    createProxyMiddleware('/api', {
      target: 'http://localhost:8080/',
      changeOrigin: true,
    }),
  );
};