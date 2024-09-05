import '@mantine/core/styles.css';
import { createRoot } from 'react-dom/client';
import React from 'react';
import { RouterProvider } from 'react-router-dom';
import { Router } from './router';
import { DEFAULT_THEME, MantineProvider } from '@mantine/core';

const root = createRoot(document.getElementById('root'));

root.render(<React.StrictMode>
  <MantineProvider theme={DEFAULT_THEME}>
    <RouterProvider router={Router} />
  </MantineProvider>
</React.StrictMode>);