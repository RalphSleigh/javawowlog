import { createRoot } from 'react-dom/client';
import React from 'react';
import { RouterProvider } from 'react-router-dom';
import { Router } from './router';

const root = createRoot(document.getElementById('root'));
root.render(<React.StrictMode>
    <RouterProvider router={Router} />
  </React.StrictMode>);