import React from 'react';
import { Redirect, Route, Router, Switch, useLocation } from 'wouter';
import { HomePage } from '../pages/HomePage';
import { Page } from '../pages/Page';

// Кастомная навигационная функция для работы с хэшем
const hashNavigate = (to: string) => {
  window.location.hash = to;
};

// Хук для получения пути из хэша
const hashLocation = () => {
  return window.location.hash.replace(/^#/, '') || '/';
};

export const AppRoutes: React.FC = () => {
  const [location] = useLocation();

  return (
    <Router hook={() => [hashLocation(), hashNavigate]}>
      <Switch>
        <Route path="/:id">{(params) => <Page pageId={params.id} />}</Route>
        <Route path="/">
          <HomePage />
        </Route>
        <Route>
          <Redirect to="/" />
        </Route>
      </Switch>
    </Router>
  );
};
