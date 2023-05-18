import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpEvent, HttpHandler, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/User';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const user: User = JSON.parse(window.sessionStorage.getItem("user")!);
        if (!req.headers.has('Content-Type') || !req.headers.has('Authorization')) {
            if (user != null) {
                req = req.clone({ setHeaders: { Authorization: `Bearer ${user.authToken}`, 'Content-Type': 'application/json'}
             });
             }
            req = req.clone({ headers: req.headers.set('Content-Type', 'application/json') });
        }
    
        req = req.clone({ headers: req.headers.set('Accept', 'application/json') });
        return next.handle(req);
    }
}

export const httpInterceptorProvider = [
    {provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true}
]