import React from 'react';
import { Route, Router, Switch } from 'wouter';
import { HomePage } from '../pages/HomePage';
import { AuthPage } from '../pages/AuthPage';

export const AppRoutes: React.FC = () => {
  return (
    <Router>
      <Switch>
        <Route path="/" component={HomePage} />
        <Route path="/auth" component={AuthPage} />
        <Route path="/:id">
          {(params) => <div>Динамическая страница: {params.id}</div>}
        </Route>
      </Switch>
    </Router>
  );
};
