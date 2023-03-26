import Router from "koa-router";
import * as authCtrl from './auth.ctrl';

const auth = new Router();

auth.post('/create', authCtrl.create);
auth.post('/login', authCtrl.login);
auth.post('/member/me', authCtrl.check);
auth.post('/logout', authCtrl.logout);

