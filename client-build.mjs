import * as esbuild from 'esbuild'
import classModules from "esbuild-plugin-class-modules"

let ctx = await esbuild.context({
  entryPoints: ['client/index.tsx'],
  bundle: true,
  outfile: 'web/bundle.js',
  sourcemap: true,
  plugins: [classModules()],
})

await ctx.watch()
console.log('Watching for changes...')
