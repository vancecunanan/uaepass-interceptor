#import <Cordova/CDV.h>
#import <WebKit/WebKit.h>

@interface UAEPassInterceptor : CDVPlugin <WKNavigationDelegate>
@end

@implementation UAEPassInterceptor

- (void)pluginInitialize {
    WKWebView* wkWebView = (WKWebView*)self.webView.engineWebView;
    wkWebView.navigationDelegate = self;
}

- (void)webView:(WKWebView *)webView
 decidePolicyForNavigationAction:(WKNavigationAction *)navigationAction
 decisionHandler:(void (^)(WKNavigationActionPolicy))decisionHandler {

    NSURL *url = navigationAction.request.URL;

    if ([[url scheme] isEqualToString:@"uaepass"]) {

        NSString *newUrlString = [[url absoluteString]
            stringByReplacingOccurrencesOfString:@"uaepass://"
            withString:@"uaepassstg://"];

        NSURL *newUrl = [NSURL URLWithString:newUrlString];

        [[UIApplication sharedApplication] openURL:newUrl options:@{} completionHandler:nil];

        decisionHandler(WKNavigationActionPolicyCancel);
        return;
    }

    decisionHandler(WKNavigationActionPolicyAllow);
}

@end
