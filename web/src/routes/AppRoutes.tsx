import React from 'react';
import { Redirect, Route, Router, Switch } from 'wouter';
import { useHashLocation } from '../hooks/useHashLocation'; // Импортируем хук
import { HomePage } from '../pages/HomePage';
import { Page } from '../pages/Page';

export const AppRoutes: React.FC = () => {
  const [location, hashNavigate] = useHashLocation(); // Используем хук

  return (
    <Router hook={() => [location, hashNavigate]}> {/* Обратите внимание на это изменение */}
      <Switch>
        <Route path="/:id">{(params) => <Page pageId={params.id} />}</Route>
        <Route path="/">
          <HomePage />
        </Route>
        <Route>
          <Redirect to={'/'} />
        </Route>
      </Switch>
    </Router>
  );
};
